package br.gov.es.conceicaodocastelo.stock.servicies;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.gov.es.conceicaodocastelo.stock.dto.OrderCreateRequestDto;
import br.gov.es.conceicaodocastelo.stock.models.Institution;
import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.Order;
import br.gov.es.conceicaodocastelo.stock.models.Request;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.servicies.generic.GenericServiceImp;
import br.gov.es.conceicaodocastelo.stock.servicies.interfaces.OrderInterface;

@Service
public class OrderService extends GenericServiceImp<Order> implements OrderInterface {

	@Autowired
	InstitutionService institutionService;

	@Autowired
	ItemService itemService;

//Long institutionId, String nameOrder, String descriptionOrder, List<Long> itemsId, List<Integer> amounts
	public Order createOrder(OrderCreateRequestDto dto) throws Exception {
		Order order = new Order();
		this.save(order);
		if (dto.nameOrder() == null) {
			order.setName("Pedido " + order.getId());
		} else {

			order.setName(dto.nameOrder());
			order.setName(order.getName().substring(0, 1).toUpperCase().concat(order.getName().substring(1)));
			order.setObservation(dto.descriptionOrder());
		}

		Institution institution = institutionService.findById(Long.parseLong(dto.institutionId()));
		institution.addOrders(order);

		List<Item> itemsRequired = new ArrayList<>();
		dto.itemsId().forEach(id -> {
			try {
				itemsRequired.add(itemService.findById(Long.parseLong(id)));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		});

		List<Request> requests = new ArrayList<>();
		for (int i = 0; i < dto.amounts().size(); i++) {
			Request prematureRequest = new Request(order, itemsRequired.get(i), Integer.parseInt(dto.amounts().get(i)));
			requests.add(prematureRequest);
		}

		order.addRequests(requests);

		return this.save(order);

	}

	public List<Order> findByName(String name) {
		if (name != null) {
			List<Order> orders = this.findByNameO(name);
			return orders;
		} else {
			throw new NullPointerException(
					"ResponseEntity<List<ItemModel>> findByName(@RequestParam(value = \"name\") String name  == null)");
		}
	}

	public void issueProofDelivery(Long id) {
		Order order;
		try {
			order = this.findById(id);
			System.out.println(order.getName());
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String date = format.format(new Date());
			try {
				//criando documento
				Document document = new Document();
				String url = "/server/public/receipts/";
				String fileName = "proofDelivery" + new Date().getTime() + ".pdf";
				PdfWriter.getInstance(document, new FileOutputStream(url + fileName));
				
				document.open();
				document.setPageSize(PageSize.A4);
				Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
				
				Paragraph title = new Paragraph("Comprovante de entrega de pedido - " + order.getInstitution().getName(), fontTitle);
				title.setAlignment(Element.ALIGN_CENTER);
				title.setSpacingAfter(20f);
				document.add(title);
				
				PdfPTable table = new PdfPTable(3);
				table.setWidthPercentage(100);
				
				Font fontSubTitleTableProperties = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
				Paragraph name = new Paragraph("Nome do item", fontSubTitleTableProperties);
				Paragraph unitType = new Paragraph("Tipo de unidade do item", fontSubTitleTableProperties);
				Paragraph amount = new Paragraph("Quantidade do item", fontSubTitleTableProperties);
				name.setAlignment(Element.ALIGN_CENTER);
				unitType.setAlignment(Element.ALIGN_CENTER);
				amount.setAlignment(Element.ALIGN_CENTER);
				
				table.addCell(new PdfPCell(name));
				table.addCell(new PdfPCell(unitType));
				table.addCell(new PdfPCell(amount));
				
				order.getRequests().forEach(r -> {
					table.addCell(new PdfPCell(new Paragraph(r.getItemName())));
					table.addCell(new PdfPCell(new Paragraph(r.getItem().getUnitType())));
					table.addCell(new PdfPCell(new Paragraph(r.getRequiredAmount().toString())));
				});
				
				document.add(table);
				
				document.add(new Paragraph("\n\n\n\n_________________________________ \n Gestor do Almoxarifado \n\n\n\n"));
				document.add(new Paragraph("_________________________________ \n Responsável pela instituição \n (" + order.getInstitution().getResponsible() + ")\n\n\n\n"));
				document.add(new Paragraph("_________________________________ \n Secretário Municipal de Educação\n\n\n"));
				document.add(new Paragraph("Documento gerado em " + date));
				
				
				document.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}

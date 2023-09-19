package br.gov.es.conceicaodocastelo.stock.controllers;

import br.gov.es.conceicaodocastelo.stock.dto.ItemRecordDto;
import br.gov.es.conceicaodocastelo.stock.dto.StockGroupRecordDto;
import br.gov.es.conceicaodocastelo.stock.models.Item;
import br.gov.es.conceicaodocastelo.stock.models.StockGroup;
import br.gov.es.conceicaodocastelo.stock.servicies.StockGroupService;
import jakarta.validation.Valid;
import org.hibernate.QueryException;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


@RestController
@CrossOrigin("*")
@RequestMapping(path = "/stock-group")
public class StockGroupController {


    final StockGroupService stockGroupService;

    public StockGroupController(StockGroupService stockGroupService) {
        this.stockGroupService = stockGroupService;
    }

    private List<StockGroup> stocks = new ArrayList<>();

    StockGroup stockAwaitForReturn;

    //POST'S
    @PostMapping(value = "/create")
    public ResponseEntity<StockGroup> createStockGroup(@RequestBody @Valid StockGroupRecordDto stockGroupRecordDto ) {
        System.out.println("\n\nRecebendo solicitação POST em /stock-group/create\n\n");
        StockGroup stockGroup = new StockGroup();
        BeanUtils.copyProperties(stockGroupRecordDto,stockGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockGroupService.save(stockGroup));
    }

    @PostMapping(value = "/addItem/{idGroup}")
    public ResponseEntity<StockGroup> addItemToStockGroup(@PathVariable(value = "idGroup") UUID idGroup, @RequestBody @Valid ItemRecordDto itemRecordDto) {
        Item item = new Item();

        BeanUtils.copyProperties(itemRecordDto, item);

        Optional<StockGroup> stockO = stockGroupService.findById(idGroup);

        if (stockO.isEmpty()) {
            throw new QueryException("StockGroup not found");
        }

        StockGroup stockM = stockO.get();
        stockM.addItems(item);

        stockM.getItems().forEach(i -> {
            if(i.getUnitType() == null || i.getUnitType() == "") {
                i.setUnitType("Unidade");
            }
            if(i.getAmount() == null || i.getAmount() < 0) {
                i.setAmount(0);
            }
        });


        return ResponseEntity.status(HttpStatus.OK).body(stockGroupService.save(stockM));
    }


    //GETER'S
    @GetMapping(path = "/find/byName")
    @ResponseBody
    public ResponseEntity<List<StockGroup>> findByName(@RequestParam(value = "name") String name) {
        if(stockGroupService.findByName(name).getBody() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(stockGroupService.findByName(name).getBody());
        } else {
            throw new QueryException("not found");
        }
    }

    @GetMapping(value = "/find/byId")
    @ResponseBody
    public ResponseEntity<Optional<StockGroup>> findById(@RequestParam(value = "id") UUID id) {
        if(id != null) {
            Optional<StockGroup> stock = stockGroupService.findById(id);
            if(stock.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(stockGroupService.findById(id));
            } else {
                throw new QueryException("not found");
            }
        } else {
            throw new NullPointerException("ResponseEntity<Optional<StockGroupModel>> findById(@RequestParam(value = \"id\") UUID id == null)");
        }
    }



    @GetMapping(path = "/find/all")
    @ResponseBody
    public List<StockGroup> findAll() {
        stocks.clear();
        stocks = stockGroupService.findAll();
        return stocks;
    }

    @GetMapping(path = "/getNames")
    @ResponseBody
    public List<String> getAllStockNamesAndDescription() {
        List<String> l = new ArrayList<>();
        l.clear();
        findAll().forEach(s -> {
            l.add(s.getName());
            String d = s.getDescription();
            if(d != null) {
                l.add(s.getDescription());
            } else {
                l.add("");
            }
        });
        System.out.println(l.toString());
        return l;

    }

    @GetMapping(value = "/getAllItems")
    @ResponseBody
    public List<Item> getAllItemInStockGroup(@RequestParam(value = "idGroup") UUID id) {
        if(id != null) {
            Optional<StockGroup> stock = findById(id).getBody();
            if(stock != null && stock.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(stock.get().getItems()).getBody();
            } else {
                throw new QueryException("stock not found");
            }
        }
        throw new NullPointerException("List<ItemModel> getAllItemInStockGroup(@RequestParam(value = \"idGroup\") UUID id == null)");
    }


    //DESTROYERS
    @GetMapping("/delete")
    @ResponseBody
    public void deleteStockGroupById(@RequestParam(value = "idGroup") UUID id) {
        if(id != null) {
            stockGroupService.deleteById(id);
        }
    }


    @GetMapping(value = "/report")
    @ResponseBody
    public ResponseEntity<List<String>> createReport() {
        Document document = new Document();

        List<StockGroup> list = this.findAll();

        try {
            String url = "C:\\Users\\usuario\\Documents\\sistema_gerenciamento_estoque\\stock-management-system-J-SpringBoot\\stock\\src\\main\\resources\\static\\assets\\reports\\";
            String fileName = "report" + new Date().getTime() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(url + fileName));
            document.open();
            document.setPageSize(PageSize.A4);

            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Relatório de estoque", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);
            for (StockGroup stock : list) {
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

                for (Item item : stock.getItems()) {
                    table.addCell(new PdfPCell(new Paragraph(item.getName())));
                    table.addCell(new PdfPCell(new Paragraph(item.getUnitType())));
                    table.addCell(new PdfPCell(new Paragraph(item.getAmount().toString())));

                }

                PdfPTable tituloTable = new PdfPTable(3);
                tituloTable.setWidthPercentage(100);
                PdfPCell tituloCell = new PdfPCell(new Phrase("Grupo de estoque: " + stock.getName(),
                        new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD)));
                tituloCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tituloCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tituloCell.setPadding(10);
                tituloCell.setColspan(3);
                tituloTable.addCell(tituloCell);

                document.add(tituloTable);
                document.add(table);

                document.add(new Paragraph("\n\n\n"));

            }
            SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy - EEEE - HH:mm");
            String data = formatoData.format(new Date());
            document.add(new Paragraph("\n\nRelatório gerado no dia " + data));
            document.close();
            System.out.println("criado");
            boolean whilecondition = true;
            while (whilecondition) {
                File file = new File(url+fileName);
                if(file.exists()) {
                    whilecondition = false;
                    continue;
                }
                System.out.println("\n\nnao existe ainda");
            }
            return ResponseEntity.status(HttpStatus.OK).body(List.of(url, fileName));
        } catch (Exception e) {
            System.out.println(e);
            document.close();
            throw new RuntimeException("Erro ao criar relatório");
        }
    }    
    
    
}

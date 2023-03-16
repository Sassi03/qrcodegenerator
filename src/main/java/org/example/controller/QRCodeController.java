package org.example.controller;

import org.example.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping(value = "/qrcode/png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCodePNG(@RequestParam String text,
                                                    @RequestParam int width, @RequestParam int height) throws Exception {
        byte[] qrCodeByteValue = qrCodeService.generateQRCodeImage(text, width, height);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeByteValue);
    }

    @GetMapping(value = "/qrcode/pdf")
    public ResponseEntity<byte[]> generateQRCodePDF(@RequestParam String text,
                                                    @RequestParam int width, @RequestParam int height) throws Exception {
        byte[] qrCodeByteValue = qrCodeService.generateQRCodePDF(text, width, height);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=qrcode.pdf");
        return new ResponseEntity<>(qrCodeByteValue, headers, HttpStatus.OK);
    }
}


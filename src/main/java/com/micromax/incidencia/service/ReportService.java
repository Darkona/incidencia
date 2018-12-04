package com.micromax.incidencia.service;

import com.micromax.incidencia.repository.ReportDAO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class ReportService {

    @Autowired
    private ReportDAO reportDAO;

    public JasperPrint exportPdfFile() throws SQLException, JRException, IOException {
        return reportDAO.exportPdfFile();
    }
}
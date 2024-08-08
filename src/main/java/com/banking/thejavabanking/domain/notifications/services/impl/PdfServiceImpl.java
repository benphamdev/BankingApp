package com.banking.thejavabanking.domain.notifications.services.impl;

import com.banking.thejavabanking.domain.accounts.entity.Account;
import com.banking.thejavabanking.domain.accounts.entity.UserTransaction;
import com.banking.thejavabanking.domain.accounts.repositories.AccountRepository;
import com.banking.thejavabanking.domain.notifications.dto.requests.EmailDetailRequest;
import com.banking.thejavabanking.domain.notifications.dto.requests.ExportTransactionToPdfRequest;
import com.banking.thejavabanking.domain.notifications.services.IPdfService;
import com.banking.thejavabanking.domain.users.entities.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@FieldDefaults(
        makeFinal = true,
        level = lombok.AccessLevel.PRIVATE
)
public class PdfServiceImpl implements IPdfService {
    EmailServiceImpl emailService;
    AccountRepository accountRepository;

    @Override
    public void exportTransactionToPdf(
            ExportTransactionToPdfRequest exportTransactionToPdfRequest
    ) {
        try {
            Document document = new Document(new Rectangle(PageSize.A4.rotate()), 10, 10, 10, 10);

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(exportTransactionToPdfRequest.getPdfPath())
            );

            document.open();

            // Create a table for the bank information
            PdfPTable bankInfoTable = new PdfPTable(1);
            PdfPCell spacePdfPCell = new PdfPCell(new Phrase());

            PdfPCell bankName = new PdfPCell(new Phrase("Bank Statement"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.LIGHT_GRAY);
            bankName.setPadding(10);

            PdfPCell bankAddress = new PdfPCell(new Phrase(
                    "Bank Address : Number 1 ,  VVN Street, Thu Duc City, VietNam - 123456"));
            bankAddress.setBorder(0);

            bankInfoTable.addCell(bankName);
            bankInfoTable.addCell(bankAddress);

            // Create a table for the statement information
            PdfPTable statementInfo = new PdfPTable(2);

            PdfPCell startDateCell =
                    new PdfPCell(new Phrase("Start Date: " + exportTransactionToPdfRequest.getStartDate()));
            startDateCell.setBorder(0);
            PdfPCell endDateCell =
                    new PdfPCell(new Phrase("End Date: " + exportTransactionToPdfRequest.getEndDate()));
            endDateCell.setBorder(0);

            Optional<Account> account = accountRepository.getAccountByAccountNumber(
                    exportTransactionToPdfRequest.getAccountNumber());
            User user = account.get()
                               .getUser();

            String customerName = user.getFirstName() + " " + user.getLastName();

            PdfPCell customerNameCell = new PdfPCell(new Phrase("Customer Name: " + customerName));
            customerNameCell.setBorder(0);

            PdfPCell customerAddressCell = new PdfPCell(
                    new Phrase("Customer Address: " + user.getAddresses()));

            customerAddressCell.setBorder(0);

            statementInfo.addCell(customerNameCell);
            statementInfo.addCell(customerAddressCell);
            statementInfo.addCell(startDateCell);
            statementInfo.addCell(endDateCell);

            // Create a table for the transactions

            PdfPTable transactionTable = new PdfPTable(4);

            PdfPCell dateCell = new PdfPCell(new Phrase("Date"));
            dateCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            dateCell.setBorder(0);
            PdfPCell typeCell = new PdfPCell(new Phrase("Type"));
            typeCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            typeCell.setBorder(0);
            PdfPCell amountCell = new PdfPCell(new Phrase("Amount"));
            amountCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            amountCell.setBorder(0);
            PdfPCell statusCell = new PdfPCell(new Phrase("Status"));
            statusCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            statusCell.setBorder(0);

            transactionTable.addCell(dateCell);
            transactionTable.addCell(typeCell);
            transactionTable.addCell(amountCell);
            transactionTable.addCell(statusCell);

            List<UserTransaction> transactions = exportTransactionToPdfRequest.getTransactions();
            transactions.forEach(transaction -> {
                transactionTable.addCell(new Phrase(transaction.getCreatedAt()
                                                               .toString()));
                transactionTable.addCell(new Phrase(transaction.getTransactionType()
                                                               .toString()));
                transactionTable.addCell(new Phrase(transaction.getAmount()
                                                               .toString()));
                transactionTable.addCell(new Phrase(transaction.getStatus()
                                                               .toString()));
            });

            document.add(bankInfoTable);
            document.add(statementInfo);
            document.add(transactionTable);

            document.close();

            // send email with attachment
            EmailDetailRequest emailDetail =
                    EmailDetailRequest.builder()
                                      .recipient(user.getEmail())
                                      .subject("Bank Statement")
                                      .message("List of transactions in the period from " +
                                                       exportTransactionToPdfRequest.getStartDate() + " to " +
                                                       exportTransactionToPdfRequest.getEndDate() +
                                                       " has been exported to PDF. Please check the attachment.")
                                      .attachment(exportTransactionToPdfRequest.getPdfPath())
                                      .build();

            emailService.sendEmailWithAttachment(emailDetail);

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

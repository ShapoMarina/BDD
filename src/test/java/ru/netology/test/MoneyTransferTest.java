package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MoneyTransferTest {
    DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var loginData = DataHelper.getValidAuthInfo();
        var verificationPage = loginPage.validLogin(loginData);
        var verificationCode = DataHelper.getVerificationCode(loginData);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTopUpFirstCardFromSecond() {
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var transferAmount = DataHelper.validAmount(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance + transferAmount;
        var expectedBalanceSecondCard = secondCardBalance - transferAmount;
        var transferPage = dashboardPage.topUpFirstCard();
        dashboardPage = transferPage.successfulTransfer(String.valueOf(transferAmount), secondCardInfo);
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldGetErrorMessageIfNotEnoughMoneyForTransfer() {
        var firstCardInfo = DataHelper.getFirstCard();
        var secondCardInfo = DataHelper.getSecondCard();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        var transferAmount = DataHelper.invalidAmount(firstCardBalance);
        var transferPage = dashboardPage.topUpFirstCard();
        transferPage.transfer(String.valueOf(transferAmount), secondCardInfo);
        transferPage.findErrorMessage("Ошибка! Недостаточно средств для совершения перевода.");
        var actualBalanceFirstCard = dashboardPage.getFirstCardBalance();
        var actualBalanceSecondCard = dashboardPage.getSecondCardBalance();
        assertEquals(firstCardBalance, actualBalanceFirstCard);
        assertEquals(secondCardBalance, actualBalanceSecondCard);
    }
}
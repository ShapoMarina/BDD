package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private final SelenideElement TransferHead =  $("h1").shouldHave(Condition.exactText("Пополнение карты"));
    private final SelenideElement amount = $("[data-test-id='amount'] input");
    private final SelenideElement from = $("[data-test-id='from'] .input__control");
    private final SelenideElement topUpButton = $("[data-test-id='action-transfer']");
    private final SelenideElement error = $("[data-test-id='error-notification']");

public TransferPage() {
    TransferHead.shouldBe(Condition.visible);
}

    public DashboardPage successfulTransfer(String transferAmount, DataHelper.Card Card) {
        transfer(transferAmount, Card);
        return new DashboardPage();
    }
    public void transfer(String transferAmount, DataHelper.Card Card) {
    amount.setValue(transferAmount);
    from.setValue(Card.getCardNumber());
    topUpButton.click();
    }

    public void findErrorMessage(String expectedText) {
    error.shouldHave(Condition.exactText(expectedText), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }
}



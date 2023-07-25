package ru.netology.cardDelivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @Test
    public void shouldSendForm(){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Москва");
        form.$("[data-test-id=date] input").setValue("15.08.2023");
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$(".spin_visible").shouldBe(Condition.visible  );
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(13));
    }

    @Test
    public void choseCityAndDateFromPopUpMenu(){
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue("Мо");
        $$(".popup_visible .menu-item").find(Condition.exactText("Москва")).click();
        form.$("[data-test-id=date] .icon-button").click();
        $$(".popup_visible .calendar__arrow_direction_right").last().click();
        $$(".popup_visible .calendar__day").find(Condition.exactText("4")).click();
        form.$("[data-test-id=name] input").setValue("Иванов Иван");
        form.$("[data-test-id=phone] input").setValue("+71234567890");
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$(".spin_visible").shouldBe(Condition.visible  );
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(13));
    }
}

import com.example.client.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class CheckDataTests extends Assert {
    //подключение к сокету
    @BeforeClass
    public static void Before() throws IOException {
        MainClient.socket = new Socket("localhost", 4004);
    }

    //проверка на корректный возврат пользователей из БД
    @Test
    public void checkUsersData() throws Exception{
        var users  = Users.getUsers();
        for (var item: users) {
            Assert.assertNotNull("Отсутсвует логин", item.getLogin());
            Assert.assertNotNull("Отсутсвует пароль", item.getPassword());
            Assert.assertNotNull("Отсутсвует статус", item.getStatus());
            Assert.assertNotNull("Отсутсвует доступ", item.getAccess());
        }
    }

    //проверка на корректный возврат амортизаций из БД
    @Test
    public void checkAmortizationsData() throws Exception{
        var amortization  = Amortization.getAmortization();
        for (var item: amortization) {
            Assert.assertNotNull("Отсутсвует название ОФ", item.getCapitalName());
            Assert.assertNotNull("Отсутсвует цена амортизации ОФ за год", item.getYearCostAmort());
            Assert.assertNotNull("Отсутсвует процент амортизации ОФ за год", item.getYearPerAmort());
            Assert.assertNotNull("Отсутсвует цена амортизации ОФ за месяц", item.getMonthCostAmort());
            Assert.assertNotNull("Отсутсвует процент амортизации ОФ за год", item.getYearPerAmort());
        }
    }

    //проверка на корректный возврат ОФ из БД
    @Test
    public void checkCapitalsData() throws Exception{
        var capital  = Capital.getCapital();
        for (var item: capital) {
            Assert.assertNotNull("Отсутсвует название ОФ", item.getCapitalName());
            Assert.assertNotNull("Отсутсвует цена ОФ", item.getCapitalPrice());
            Assert.assertNotNull("Отсутсвует срок полезности ОФ", item.getUsefullLife());
        }
    }

}

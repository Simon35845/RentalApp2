package itacademy.rentalapp2.exceptions;

public class DatabaseErrors {
    public static final String ADDRESS_ALREADY_EXISTS = "Адрес с такими значениями уже существует! " +
            "Введите другие данные.";
    public static final String ADDRESS_HAS_LINKED_APARTMENTS = "Адрес содержит привязанные квартиры! " +
            "Для удаления этого адреса, удалите все привязанные квартиры.";
    public static final String ADDRESS_NOT_FOUND = "Адрес не найден";
}

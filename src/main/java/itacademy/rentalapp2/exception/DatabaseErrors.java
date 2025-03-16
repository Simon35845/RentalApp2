package itacademy.rentalapp2.exception;

public class DatabaseErrors {
    public static final String ADDRESS_ALREADY_EXISTS = "Адрес с такими значениями уже существует! " +
            "Введите другие данные.";
    public static final String ADDRESS_NOT_FOUND = "Адрес не найден";
    public static final String ADDRESS_HAS_LINKED_APARTMENTS = "Адрес содержит привязанные квартиры! " +
            "Для удаления этого адреса, удалите все привязанные квартиры.";

    public static final String APARTMENT_ALREADY_EXISTS = "Квартира с такими значениями уже существует! " +
            "Введите другие данные.";
    public static final String APARTMENT_NOT_FOUND = "Квартира не найдена";

    public static final String LANDLORD_ALREADY_EXISTS = "Арендодатель с таким e-mail уже существует! " +
            "Введите другие данные.";
    public static final String LANDLORD_NOT_FOUND = "Арендодатель не найден";

    public static final String APARTMENT_NOT_LINKED_TO_LANDLORD  = "Квартира не присоединена к арендодателю";
}

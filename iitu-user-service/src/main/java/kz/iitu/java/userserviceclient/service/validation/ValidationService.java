package kz.iitu.java.userserviceclient.service.validation;



import kz.iitu.java.userserviceclient.constants.DEFConstants;
import kz.iitu.java.userserviceclient.exceptions.CustomBadRequestException;
import kz.iitu.java.userserviceclient.payload.request.ClientFullInfoRequest;
import kz.iitu.java.userserviceclient.payload.request.RegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ValidationService {

    public static void saveClientInfoValidation(ClientFullInfoRequest request){
        fieldsNotEmptyAvailability(request);
        fieldsCyrillicAlphabetAvailability(request);
        iinAvailability(request.getIin() , request.getBirthDate() , request.getGender());
        documentNumberAvailability(request.getDocumentNumber());
   }

    private static void documentNumberAvailability(String number){
        Pattern pattern = Pattern.compile("\\d{9}$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches())
            throw new CustomBadRequestException("Номер удостоверения неправильный");
    }


    private static void iinAvailability(String iin , LocalDate birthDate , String gender){
        if (iin.isEmpty())
            throw new CustomBadRequestException("Поле ИИН обязательное");
        if (birthDate==null)
            throw new CustomBadRequestException("Поле дата рождения обязательное");
        if (gender.isEmpty())
            throw new CustomBadRequestException("Поле пол обязательное");
        if (!gender.equals("male")  && !gender.equals("female"))
            throw new CustomBadRequestException("Выберите male или female");
        String month = String.valueOf(birthDate.getMonthValue());
        Map<String  , String > data = new HashMap<>();
        data.put("iin" , iin);
        data.put("year" , String.valueOf(birthDate.getYear()));
        data.put("month" , month.length()<2?"0"+month:month);
        data.put("day" , String.valueOf(birthDate.getDayOfMonth()));
        data.put("gender" , gender);
        firstPartOfIinAvailability(data);
        secondPartOfIinAvailability(data);
        thirdPartOfIinAvailability(data);
        forthPartOfIinAvailability(data);
    }

    private static void firstPartOfIinAvailability(Map<String  , String > data){
        Pattern pattern = Pattern.compile("\\d{12}$");
        Matcher matcher = pattern.matcher(data.get("iin"));
        if (!matcher.matches())
            throw new CustomBadRequestException("ИИН содержит 12 цифр");
        if (!data.get("iin").substring(0 , 2).equals(data.get("year").substring(2))) {
            log.debug("Here firstPartOfIinAvailability");
            throw new CustomBadRequestException("Проверьте иин");
        }
    }

    private static void secondPartOfIinAvailability(Map<String  , String > data){
        if (!data.get("iin").substring(2 , 4).equals(data.get("month"))) {
            log.debug("Here secondPartOfIinAvailability");
            throw new CustomBadRequestException("Проверьте иин");
        }
    }

    private static void thirdPartOfIinAvailability(Map<String  , String > data){
        if (!data.get("iin").substring(4 , 6).equals(data.get("day"))) {
            log.debug("Here thirdPartOfIinAvailability");
            throw new CustomBadRequestException("Проверьте иин");
        }
    }

    private static void forthPartOfIinAvailability(Map<String  , String > data){
        LocalDate birthDate = LocalDate.of(Integer.parseInt(data.get("year")) , Integer.parseInt(data.get("month")) ,
                Integer.parseInt(data.get("day")));
        if (!data.get("iin").substring(6 , 7).equals(centuryAndGender(birthDate , data.get("gender")))) {
            log.debug("Here forthPartOfIinAvailability");
            System.err.println(centuryAndGender(birthDate , data.get("gender")));
            throw new CustomBadRequestException("Проверьте ИИН");
        }

    }

    private static String centuryAndGender(LocalDate birthDate , String gender){
        LocalDate nineteenthCenturyStart = LocalDate.of(1801 , 1 , 1);
        LocalDate nineteenthCenturyStartEnd = LocalDate.of(1900 , 12 , 31);
        LocalDate twentiethCenturyStart = LocalDate.of(1901 , 1 , 1);
        LocalDate twentiethCenturyEnd = LocalDate.of(2000 , 12 , 31);
        LocalDate twentyFirstCenturyStart = LocalDate.of(2001 , 1 , 1);
        LocalDate twentyFirstCenturyEnd = LocalDate.of(2100 , 12 , 31);
        if (birthDate.isAfter(nineteenthCenturyStart) && birthDate.isBefore(nineteenthCenturyStartEnd))
            return gender.equals("male")?"1":"2";
        else if (birthDate.isAfter(twentiethCenturyStart) && birthDate.isBefore(twentiethCenturyEnd))
            return gender.equals("male")?"3":"4";
        else if (birthDate.isAfter(twentyFirstCenturyStart) && birthDate.isBefore(twentyFirstCenturyEnd))
            return gender.equals("male")?"5":"6";
        else {
            log.debug("Here centuryAndGender");
            throw new CustomBadRequestException("Проверьте иин");
        }
    }

    public static void registrationValidation(RegisterRequest request){
        phoneNumberAvailability(request.getPhoneNumber());
        passwordValidation(request.getPassword());
    }

    private static void phoneNumberAvailability(String phoneNumber){
        if (phoneNumber.isEmpty())
            throw new CustomBadRequestException("Поле номер телефона обязательное");
        Pattern pattern = Pattern.compile("^[+]\\d[(]\\d{3}[)]\\d{3}-\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches())
            throw new CustomBadRequestException(
                    "Ошибка валидаций номера телефона"
            );
        if (Arrays.stream(DEFConstants.values()).noneMatch(x -> x.getName()
                .equals(convertPhoneNumber(phoneNumber).substring(1 , 4))))
            throw new CustomBadRequestException("Формат DEF-кода номера телефона неправильный");
    }

    private static void passwordValidation(String password) {
        if (password == null)
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Поле пароля обязательное"
            );
        Pattern pattern = Pattern.compile("^(?=.*?[A-ZА-Я])(?=.*?[a-zа-я])(?=.*?[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches())
            throw new CustomBadRequestException("Пароль должен содержать хотя бы одну большую букву , " +
                    "одну маленькую букву, одну цифру и минимум 8 символов");
    }

    private static String convertPhoneNumber(String s) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                res = res + s.charAt(i);
            }
        }
        return res;
    }
    private static void fieldsNotEmptyAvailability(ClientFullInfoRequest request){
        if (request.getLastname().isEmpty() || request.getFirstname().isEmpty() || request.getMiddleName().isEmpty() ||
                request.getDocumentIssuedBy().isEmpty() || request.getDocumentIssuedDate()==null ||
                request.getDocumentValidUntilDate()==null)
            throw new CustomBadRequestException("Заполните все поля");
    }

    private static void fieldsCyrillicAlphabetAvailability(ClientFullInfoRequest request){
        if (!cyrillicAlphabetAvailability(request.getFirstname()) || !cyrillicAlphabetAvailability(request.getLastname()) ||
                !cyrillicAlphabetAvailability(request.getMiddleName()) || !cyrillicAlphabetAvailability(request.getDocumentIssuedBy()))
            throw new CustomBadRequestException("Используйте кириллицу");
    }

    private static boolean cyrillicAlphabetAvailability(String s){
        Pattern pattern = Pattern.compile("[а-яёА-ЯЁ]+");
        Matcher matcher = pattern.matcher(s.replaceAll("\\s+",""));
        return matcher.matches();
    }
}

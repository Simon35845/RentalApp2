package itacademy.rentalapp2.advice;

import itacademy.rentalapp2.exceptions.CustomException;
import itacademy.rentalapp2.exceptions.DatabaseErrors;
import itacademy.rentalapp2.exceptions.ServiceErrors;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ModelAndView handleCustomException(CustomException ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ex.getErrorCode());
        return mav;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleDataIntegrityViolationException(Exception ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", DatabaseErrors.ADDRESS_ALREADY_EXISTS);
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ServiceErrors.UNEXPECTED_ERROR);
        return mav;
    }
}
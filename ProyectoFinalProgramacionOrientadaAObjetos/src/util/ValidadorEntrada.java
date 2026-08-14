package util;

import Excepciones.ParametroInvalidoException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidadorEntrada {

    private ValidadorEntrada() {
    }

    public static String validarParametro(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }

        return parametro;
    }

    public static int validarEntero(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }

        try {
            return Integer.parseInt(parametro);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " debe ser un número entero.");
        }
    }

    public static double validarDecimal(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }

        try {
            return Double.parseDouble(parametro);
        } catch (NumberFormatException e) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " debe ser un número válido.");
        }
    }

    public static LocalDate validarFecha(String parametro, String nombreParametro) throws ParametroInvalidoException {
        if (parametro == null || parametro.isBlank()) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " no puede estar vacío.");
        }

        try {
            return LocalDate.parse(parametro);
        } catch (DateTimeParseException e) {
            throw new ParametroInvalidoException("El campo " + nombreParametro + " debe tener el formato YYYY-MM-DD.");
        }
    }
}
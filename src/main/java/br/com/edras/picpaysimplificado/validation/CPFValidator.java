package br.com.edras.picpaysimplificado.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public void initialize(CPF constraintAnnotation) {}

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isEmpty()) {
            return true;
        }

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {

            int soma = 0;

            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }

            int primeiroDigito = 11 - (soma % 11);

            if (primeiroDigito >= 10) {
                primeiroDigito = 0;
            }

            if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigito) {
                return false;
            }

            soma = 0;

            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }

            int segundoDigito = 11 - (soma % 11);

            if (segundoDigito >= 10) {
                segundoDigito = 0;
            }

            return Character.getNumericValue(cpf.charAt(10)) == segundoDigito;

        } catch (Exception e) {
            return false;
        }
    }
}

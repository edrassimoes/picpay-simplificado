package br.com.edras.picpaysimplificado.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public void initialize(CNPJ constraintAnnotation) {
        // Método chamado uma vez na inicialização (pode ficar vazio)
    }

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (cnpj == null || cnpj.isEmpty()) {
            return true;
        }

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        try {

            int[] pesosPrimeiroDigito = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            
            int soma = 0;

            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesosPrimeiroDigito[i];
            }

            int primeiroDigito = soma % 11;

            primeiroDigito = (primeiroDigito < 2) ? 0 : (11 - primeiroDigito);

            if (Character.getNumericValue(cnpj.charAt(12)) != primeiroDigito) {
                return false;
            }

            int[] pesosSegundoDigito = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            
            soma = 0;

            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * pesosSegundoDigito[i];
            }

            int segundoDigito = soma % 11;

            segundoDigito = (segundoDigito < 2) ? 0 : (11 - segundoDigito);

            return Character.getNumericValue(cnpj.charAt(13)) == segundoDigito;

        } catch (Exception e) {
            return false;
        }
    }
}

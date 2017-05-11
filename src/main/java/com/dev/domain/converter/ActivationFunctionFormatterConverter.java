package com.dev.domain.converter;

import com.dev.domain.model.ActivationFunction;

public class ActivationFunctionFormatterConverter {

    public static String convert(ActivationFunction activationFunction) {
        if (activationFunction == null) {
            return "";
        }
        switch (activationFunction) {
            case LOGISTIC:
                return "Сигмоида";
            case EXP:
                return "Экспоненциальная";
            case SIN:
                return "Синус";
            case HYPERBOLIC:
                return "Гиперболический тангенс";
            default:
                return "";
        }
    }
}

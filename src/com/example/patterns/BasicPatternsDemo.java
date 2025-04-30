package com.example.patterns;

public class BasicPatternsDemo {
    public static void main(String[] args) {
        System.out.println("=== Basic Patterns Demo ===");

        // Singleton
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        System.out.println("Same instance? " + (logger1 == logger2));
        logger1.log("Teste de log via Singleton");

        // Factory Method
        Notifier email = NotifierFactory.create("EMAIL");
        Notifier sms = NotifierFactory.create("SMS");
        email.notify("Olá por Email!");
        sms.notify("Olá por SMS!");

        // Strategy
        PaymentContext context = new PaymentContext(new CreditCardPayment());
        context.pay(150.00);
        context.setStrategy(new PayPalPayment());
        context.pay(200.00);
    }

    // Singleton Pattern: Logger
    public static class Logger {
        private static Logger instance;
        private Logger() {}
        public static synchronized Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }
        public void log(String msg) {
            System.out.println("[LOG] " + msg);
        }
    }

    // Factory Method Pattern: Notifier
    public interface Notifier {
        void notify(String message);
    }
    public static class EmailNotifier implements Notifier {
        public void notify(String message) {
            System.out.println("[EMAIL] " + message);
        }
    }
    public static class SMSNotifier implements Notifier {
        public void notify(String message) {
            System.out.println("[SMS] " + message);
        }
    }
    public static class NotifierFactory {
        public static Notifier create(String type) {
            switch (type.toUpperCase()) {
                case "EMAIL": return new EmailNotifier();
                case "SMS":   return new SMSNotifier();
                default: throw new IllegalArgumentException("Tipo não suportado: " + type);
            }
        }
    }

    // Strategy Pattern: Payment
    public interface PaymentStrategy {
        void pay(double amount);
    }
    public static class CreditCardPayment implements PaymentStrategy {
        public void pay(double amount) {
            System.out.println("Pago R$" + amount + " via Cartão de Crédito");
        }
    }
    public static class PayPalPayment implements PaymentStrategy {
        public void pay(double amount) {
            System.out.println("Pago R$" + amount + " via PayPal");
        }
    }
    public static class PaymentContext {
        private PaymentStrategy strategy;
        public PaymentContext(PaymentStrategy strategy) {
            this.strategy = strategy;
        }
        public void setStrategy(PaymentStrategy strategy) {
            this.strategy = strategy;
        }
        public void pay(double amount) {
            strategy.pay(amount);
        }
    }
}

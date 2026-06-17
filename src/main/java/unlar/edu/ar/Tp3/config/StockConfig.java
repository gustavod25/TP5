package unlar.edu.ar.Tp3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "inventario.stock")
public record StockConfig(int minimo, int critico) {
}

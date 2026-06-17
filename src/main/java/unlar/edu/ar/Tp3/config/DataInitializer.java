package unlar.edu.ar.Tp3.config;

import unlar.edu.ar.Tp3.dto.*;

import unlar.edu.ar.Tp3.service.CategoriaService;
import unlar.edu.ar.Tp3.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

        private final CategoriaService categoriaService;
        private final ProductoService productoService;

        public DataInitializer(CategoriaService categoriaService,
                        ProductoService productoService) {
                this.categoriaService = categoriaService;
                this.productoService = productoService;
        }

        @Override
        public void run(String... args) {
                // Crear categorÃ­as
                var electronico = categoriaService
                                .crear(new CategoriaRequest("ElectrÃ³nicos", "Dispositivos y gadgets"));
                var alimentos = categoriaService.crear(new CategoriaRequest("Alimentos", "Productos alimenticios"));
                var limpieza = categoriaService.crear(new CategoriaRequest("Limpieza", "Productos de limpieza"));

                // Crear productos con distintos niveles de stock para probar alertas
                productoService.crear(
                                new ProductoRequest("Notebook Dell XPS 15", "Laptop alto rendimiento", 1599.99, 25,
                                                electronico.id()));
                productoService
                                .crear(new ProductoRequest("Mouse Logitech MX3", "Mouse inalÃ¡mbrico", 89.99, 8,
                                                electronico.id())); // stock
                                                                    // bajo
                                                                    // (<
                                                                    // 10)
                productoService
                                .crear(new ProductoRequest("Teclado MecÃ¡nico", "Switches Cherry MX", 159.99, 2,
                                                electronico.id())); // stock
                                                                    // crÃ­tico
                                                                    // (<
                                                                    // 3)
                productoService.crear(
                                new ProductoRequest("Arroz Integral 1kg", "Arroz integral orgÃ¡nico", 3.50, 100,
                                                alimentos.id()));
                productoService.crear(
                                new ProductoRequest("Detergente 1L", "Detergente concentrado", 2.99, 5, limpieza.id())); // stock
                                                                                                                         // bajo
        }
}

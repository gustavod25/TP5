package unlar.edu.ar.Tp3.service;

import unlar.edu.ar.Tp3.model.Categoria;
import unlar.edu.ar.Tp3.model.Producto;
import unlar.edu.ar.Tp3.repository.CategoriaRepository;
import unlar.edu.ar.Tp3.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerformanceReportService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    private static final int[] DATASET_SIZES = { 1_000, 10_000, 100_000 };

    public PerformanceReportService(ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Map<String, Object> generarReporte() {
        Map<String, Object> reporte = new LinkedHashMap<>();
        reporte.put("descripcion", "Reporte de complejidad algorÃ­tmica Big O");
        reporte.put("unidad", "nanosegundos (ns)");

        // Crear una categorÃ­a de prueba
        Categoria catPrueba = categoriaRepository.save(
                new Categoria(null, "TEST_PERF", "CategorÃ­a temporal de performance"));

        List<Map<String, Object>> mediciones = new ArrayList<>();

        for (int size : DATASET_SIZES) {
            Map<String, Object> resultado = new LinkedHashMap<>();
            resultado.put("dataset", size + " registros");

            // Insertar 'size' productos de prueba
            List<Long> ids = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Producto p = productoRepository.save(
                        new Producto(null, "Prod" + i, "desc", 10.0, 100, catPrueba));
                ids.add(p.getId());
            }

            // Medir findAll (O(n))
            long t0 = System.nanoTime();
            productoRepository.findAll();
            resultado.put("GET /api/productos (O(n))", System.nanoTime() - t0 + " ns");

            // Medir findById (O(1))
            Long testId = ids.get(ids.size() / 2);
            t0 = System.nanoTime();
            productoRepository.findById(testId);
            resultado.put("GET /api/productos/{id} (O(1))", System.nanoTime() - t0 + " ns");

            // Medir save/PUT (O(1))
            t0 = System.nanoTime();
            productoRepository.save(new Producto(testId, "Updated", "desc", 10.0, 50, catPrueba));
            resultado.put("PUT /api/productos/{id} (O(1))", System.nanoTime() - t0 + " ns");

            // Medir buscarPorNombre (O(nÂ·m))
            t0 = System.nanoTime();
            productoRepository.buscarPorNombre("Prod5");
            resultado.put("GET /api/productos/buscar (O(nÂ·m))", System.nanoTime() - t0 + " ns");

            // Medir sort â€” O(n log n)
            t0 = System.nanoTime();
            productoRepository.findAll().stream()
                    .sorted((a, b) -> a.getNombre().compareTo(b.getNombre()))
                    .toList();
            resultado.put("GET /api/productos/ordenados (O(n log n))", System.nanoTime() - t0 + " ns");

            // Limpiar productos de prueba
            for (Long id : ids) {
                productoRepository.deleteById(id);
            }

            mediciones.add(resultado);
        }

        // Limpiar categorÃ­a de prueba
        categoriaRepository.deleteById(catPrueba.getId());

        reporte.put("mediciones", mediciones);
        return reporte;
    }
}

package edu.tudai.microserviciofactura.service;

import edu.tudai.microserviciofactura.entity.DetalleFactura;
import edu.tudai.microserviciofactura.repository.DetalleFacturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleFacturaService {

    private final DetalleFacturaRepository detalleFacturaRepository;

    @Transactional(readOnly = true)
    public List<DetalleFactura> findAll() {
        return detalleFacturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public DetalleFactura findById(Long id) {
        return detalleFacturaRepository.findById(id).orElse(null);
    }

    @Transactional
    public DetalleFactura save(DetalleFactura detalleFactura) {
        return detalleFacturaRepository.save(detalleFactura);
    }

    @Transactional
    public DetalleFactura update(DetalleFactura detalleFactura) {
        return detalleFacturaRepository.save(detalleFactura);
    }

    @Transactional
    public void delete(Long id) {
        detalleFacturaRepository.deleteById(id);
    }
}

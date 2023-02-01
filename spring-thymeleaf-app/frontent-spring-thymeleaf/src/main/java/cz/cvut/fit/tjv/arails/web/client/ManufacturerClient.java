package cz.cvut.fit.tjv.arails.web.client;

import cz.cvut.fit.tjv.arails.web.model.ManufacturerModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ManufacturerClient {
    private static final String ID_URI = "/{id}";
    private final WebClient manufacturerWebClient;

    public ManufacturerClient() {
        manufacturerWebClient = WebClient.create("http://localhost:8080/api/manufacturers");
    }

    public Flux<ManufacturerModel> readAll() {
        return manufacturerWebClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ManufacturerModel.class);
    }

    public Mono<ManufacturerModel> create(ManufacturerModel newManufacturer) {
        return manufacturerWebClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newManufacturer)
                .retrieve()
                .bodyToMono(ManufacturerModel.class);
    }

    public Flux<ManufacturerModel> readManufacturersByYear(Integer productionYear) {
        return manufacturerWebClient
                .get()
                .uri("/trains/{productionYear}", productionYear )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ManufacturerModel.class);
    }
}

package cz.cvut.fit.tjv.arails.web.client;

import cz.cvut.fit.tjv.arails.web.model.ManufacturerModel;
import cz.cvut.fit.tjv.arails.web.model.TrainModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainClient {
    private static final String ID_URI = "/{id}";
    private final WebClient trainWebClient;

    public TrainClient() {
        trainWebClient = WebClient.create("http://localhost:8080/api/trains");
    }

    public Flux<TrainModel> readAll() {
        return trainWebClient
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(TrainModel.class);
    }

    public Mono<TrainModel> readById( Integer id ) {
        return trainWebClient
                .get()
                .uri(ID_URI, id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TrainModel.class);
    }

    public Mono<TrainModel> create(TrainModel newTrain) {
        return trainWebClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newTrain)
                .retrieve()
                .bodyToMono(TrainModel.class);
    }

    public Mono<TrainModel> update(TrainModel updateTrain) {
        return trainWebClient
                .put()
                .uri(ID_URI, updateTrain.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updateTrain)
                .retrieve()
                .bodyToMono(TrainModel.class);
    }

    public Mono<TrainModel> updateRevisionValidity(Integer id, LocalDate revisionValidity) {
        Map<String, LocalDate> body = new HashMap<>();
        body.put("revision-validity", revisionValidity);
        return trainWebClient
                .put()
                .uri(ID_URI + "/revision-validity", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(TrainModel.class);
    }

    public Mono<Void> delete(Integer id) {
        return trainWebClient
                .delete()
                .uri(ID_URI, id)
                .retrieve()
                .bodyToMono(Void.TYPE);
    }
}

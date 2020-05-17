package com.github.osvaldsoza.ifood.cadastro.resources;

import com.github.osvaldsoza.ifood.cadastro.entitidades.Prato;
import com.github.osvaldsoza.ifood.cadastro.entitidades.Restaurante;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name="restaurante")
public class RestauranteResource {

    @GET
    public List<Restaurante> buscar() {
        return Restaurante.listAll();
    }

    @POST
    @Transactional
    public Response adicionar(Restaurante restauranteDTO) {
        restauranteDTO.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, Restaurante restauranteDTO) {
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(id);
        if (restauranteOP.isEmpty()) {
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteOP.get();
        restaurante.nome = restauranteDTO.nome;
        restaurante.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(id);
        restauranteOP.ifPresentOrElse(Restaurante::delete, () -> {
            throw new NotFoundException();
        });
    }
}

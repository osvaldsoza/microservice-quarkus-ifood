package com.github.osvaldsoza.ifood.cadastro.resources;

import com.github.osvaldsoza.ifood.cadastro.entidades.Prato;
import com.github.osvaldsoza.ifood.cadastro.entidades.Restaurante;
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
public class PratoResource {

    @GET
    @Path("{idRestaurante}/{pratos}")
    @Tag(name="prato")
    public List<Prato> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
        Optional<Restaurante> restauranteOP = getRestaurante(idRestaurante);
        return Prato.list("restaurante", restauranteOP.get());
    }

    @POST
    @Path("{idRestaurante}/{pratos}")
    @Transactional
    @Tag(name="prato")
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, Prato pratoDTO) {
        Optional<Restaurante> restauranteOP = getRestaurante(idRestaurante);
        Prato prato = new Prato();
        prato.nome = pratoDTO.nome;
        prato.descricao = pratoDTO.descricao;
        prato.preco = pratoDTO.preco;
        prato.restaurante = restauranteOP.get();
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/{pratos}/{id}")
    @Transactional
    @Tag(name="prato")
    public void atualizarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato pratoDTO) {
        getRestaurante(idRestaurante);
        Optional<Prato> pratoOP = Prato.findByIdOptional(id);
        Prato prato = pratoOP.get();
        prato.preco = pratoDTO.preco;
        prato.persist();
    }

    @DELETE
    @Path("{idRestaurante}/{pratos}/{id}")
    @Transactional
    @Tag(name="prato")
    public void deletarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
        getRestaurante(idRestaurante);
        Optional<Prato> pratoOP = Prato.findByIdOptional(id);
        pratoOP.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException();
        });
    }

    private Optional<Restaurante> getRestaurante(@PathParam("idRestaurante") Long idRestaurante) {
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(idRestaurante);
        if (restauranteOP.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }
        return restauranteOP;
    }
}

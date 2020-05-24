package demo.resurces;

import com.google.common.base.Optional;
import demo.api.Pizza;
import com.codahale.metrics.annotation.Timed;
import demo.dao.IPizzaDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/v1/pizza")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class PizzaResurces {

    IPizzaDAO pizzaDAO;

    public PizzaResurces(IPizzaDAO pizzaDAO) {
        this.pizzaDAO = pizzaDAO;
    }

    @GET
    @Path("/getall")
    @Timed
    public List<Pizza> getAll() {
        return pizzaDAO.getAll();
    }

    @GET
    @Path("/{id}")
    public Pizza get(@PathParam("id") Integer id) {
        return pizzaDAO.findById(id);
    }

    @GET
    @Timed
    public Pizza get(@QueryParam("name") Optional<String> name) {
        final String value = String.format(name.or("Pizza"));
        Pizza pizza = pizzaDAO.findByName(name.or("Pizza"));

        if (pizza == null) {
            return new Pizza(value, 0.0, 0, 0);
        }

        return pizza;

    }


    @PUT
    @Timed
    public Pizza update(@QueryParam("count") Integer count, @QueryParam("id") Integer id) {

        Pizza updatePizza = new Pizza("PizzaUpd",
                0.00, count, id);
        pizzaDAO.update(updatePizza);

        return pizzaDAO.findById(id);
    }

    @DELETE
    @Path("/del/{id}")
    public void delete(@PathParam("id") Integer id) {
        pizzaDAO.deleteById(id);
    }


    @POST
    @Timed
    @Path("/add")
    public Response addPizza(@QueryParam("name") String name,
                             @QueryParam("price") String price,
                             @QueryParam("count") String count,
                             @QueryParam("id") String id) {

        if (pizzaDAO.findByName(name) != null)
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Pizza " + name +
                            " already exists").type("text/plain").build();

        double priceToUse;
        try {
            priceToUse = new Double(price);
        } catch (NumberFormatException e) {
            priceToUse = 0.0;
        }
        int countToUse;
        try {
            countToUse = new Integer(count);
        } catch (NumberFormatException e) {
            countToUse = 0;
        }
        int idToUse;
        try {
            idToUse = new Integer(count);
            if (pizzaDAO.findById(idToUse) != null)
                return Response.status(Response.Status.BAD_REQUEST).
                        entity("Pizza with id = " + id +
                                " already exists").type("text/plain").build();
        } catch (NumberFormatException e) {
            idToUse = 0;
        }
        pizzaDAO.insert(new Pizza(name, priceToUse,
                countToUse, idToUse));

        return Response.status(Response.Status.OK).
                entity(pizzaDAO.findByName(name)).type("application/json").build();
    }
}
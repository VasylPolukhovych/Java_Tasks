
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/pizza")
public class PizzaResource {

    @Produces({"application/xml", "application/json"})
    @Path("{name}")
    @GET
    public Pizza getPizza(@PathParam("name") String name) {

        Pizza pizza = PizzaService.getPizza(name);

        if (pizza == null) {
            return new Pizza("NOT FOUND", 0.0, 0);
        }

        return pizza;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response addPizza(@FormParam("name") String name,
                             @FormParam("price") String price,
                             @FormParam("count") String count) {

        if (PizzaService.getPizza(name) != null)
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
            countToUse = (int) price;
        } catch (NumberFormatException e) {
            countToUse = 0;
        }
        PizzaService.addPizza(new Pizza(name, priceToUse,
                countToUse));

        return Response.ok().build();
    }
}
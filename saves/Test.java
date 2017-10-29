public class Test extends Model.Panzer {
    public Test(Model.Landschaft landschaft, Controller.AktionenButtonController controller) {
        super(landschaft,controller);
    }
    public void main() {
vor();
while(true) {
if (vornFrei())
{
vor(); 
} else {
linksUm();
}
}

    }}
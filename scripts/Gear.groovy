import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getGear(){
	String type= "vexGear"
	if (args==null) {
		args=["84T"]
	}
	StringParameter size = new StringParameter(type + " Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexGearConfig = Vitamins.getConfiguration( type,size.getStrValue())

	CSG round = new Cylinder(vexGearConfig.diameter/2, vexGearConfig.diameter/2, vexGearConfig.height, (int)30).toCSG()
	
  	/*CSG stub = sheet.difference(hole)
  	CSG outputSheet = stub.clone()
  	for (int i=0;i< vexSheetConfig.x;i++){
  		for (int j=0;j<vexSheetConfig.y;j++){
  			outputSheet = outputSheet.union(
  					stub
  						.movex(i*side)
  						.movey(j*side)
  					
  				)
  		}
  	}*/
  	//outputSheet = outputSheet.union(new Cube(side).toCSG())
	return round
		.setParameter(size)
		.setRegenerate({getGear()})
}
return getGear()
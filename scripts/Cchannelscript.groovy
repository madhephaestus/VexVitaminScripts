import com.neuronrobotics.bowlerstudio.vitamins.Vitamins;
import eu.mihosoft.vrl.v3d.parametrics.*;

CSG getSheet(){
	String type= "vexCchannel"
	if(args==null)
		args=["2x5"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexSheetConfig = Vitamins.getConfiguration( type,size.getStrValue())

	double thickness = 0.8
	double side = 12.5

  
   	CSG sheet = new Cube(	side,// X dimention
			side,// Y dimention
			thickness//  Z dimention
			).toCSG()// this converts from the geometry to an object we can work with
			
	CSG hole = new Cube(	4.7,// X dimention
			4.7,// Y dimention
			thickness*2//  Z dimention
			).toCSG()// this converts from the geometry to an object we can work with
	
	CSG midhole = new Cube( 4.7,4.7,thickness*2).toCSG()
	
			
  	CSG stub = sheet.difference(hole)
  	CSG outputSheet = stub.clone()
  	System.out.println("Sucess")
  	System.out.println(vexSheetConfig.y)
  	/*if (vexSheetConfig.x != 2)
	{
	for (int i=0;i< vexSheetConfig.x;i++){
  		for (int j=0;j<vexSheetConfig.y;j++){
  			outputSheet = outputSheet.union(
  					stub
  						.movex(i*side)
  						.movey(j*side)
  					
  				)
  		}
  	}
	}
	else
	{
		for (int i=0;i< vexSheetConfig.x;i++){
  		for (int j=0;j<vexSheetConfig.y;j++){
  			outputSheet = outputSheet.union(
  					stub
  						.movex(i*side)
  						.movey(j*side)
  					
  				)
			}
		}
		for (int x=0;x<vexSheetConfig.y;x++){	
			outputSheet = outputSheet.difference(midhole.movey(1.54*x))
		}
	}
	*/
	for (int i=0;i< vexSheetConfig.x;i++){

		
		
  		for (int j=0;j<vexSheetConfig.y;j++){

  			if (i == 0 || i == vexSheetConfig.x -1)
  			{
  				outputSheet.union(stub.movex(-(i*side)).rotz(90))
  			}
  			
  			outputSheet = outputSheet.union(
  					stub
  						.movex(i*side)
  						.movey(j*side))
				
  		}
	}

  
			
  	//outputSheet = outputSheet.union(new Cube(side).toCSG())
	return outputSheet
		.setParameter(size)
		.setRegenerate({getSheet()})

		
}

/*
CSG getNut(){
	String type= "vexFlatSheet"
	if(args==null)
		args=["1x5"]
	StringParameter size = new StringParameter(	type+" Default",
										args.get(0),
										Vitamins.listVitaminSizes(type))
	//println "Database loaded "+database
	HashMap<String,Object> vexSheetConfig = Vitamins.getConfiguration( type,size.getStrValue())

	double thickness = 0.8
	double side = 12.5

	double nunRad = ((side/3)/(2*Math.sin(Math.PI/8)))*3/2
	CSG head =new Cylinder(nunRad,nunRad,thickness,(int)8).toCSG() 
				.rotz(45/2)
  
   	CSG sheet = new Cube(	side,// X dimention
			side,// Y dimention
			thickness//  Z dimention
			).toCSG()// this converts from the geometry to an object we can work with
			.intersect(head)
	CSG hole = new Cube(	4.7,// X dimention
			4.7,// Y dimention
			thickness*2//  Z dimention
			).toCSG()// this converts from the geometry to an object we can work with
			
  	CSG stub = sheet.difference(hole)
  	CSG outputSheet = stub.clone()
  	for (int i=0;i< vexSheetConfig.x;i++){
  		for (int j=0;j<vexSheetConfig.y;j++){
  			outputSheet = outputSheet.union(
  					stub
  						.movex(i*side)
  						.movey(j*side)
  					
  				)
  		}
  	}
  	//outputSheet = outputSheet.union(new Cube(side).toCSG())
	return outputSheet
		.setParameter(size)
		.setRegenerate({getNut()})
}
return getNut()
*/
return getSheet()
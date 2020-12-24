function FlushPage(field){
    $("#ShowInfo").empty();
    const url = "/zhm/Airport/Manager/" + field;   // "/zhm/Airport/Manager/FlushFlight"
    $.getJSON(url, null, function(data){
        if(field == "FlushFlight") {
            for(var i in data) {
                var newtr = document.createElement("tr");
                newtr.className = "active";
                var FlightID = document.createElement("td");
                var FlightCompany = document.createElement("td");
                var BeginPos = document.createElement("td");
                var DestPos = document.createElement("td");
                var Stopover = document.createElement("td");
                var PlaneGo = document.createElement("td");
                var PlaneArrive = document.createElement("td");
                var ActuallyArrive = document.createElement("td");
                var FlightBuilding = document.createElement("td");
                var GoDoor = document.createElement("td");
                var ReadyPos = document.createElement("td");
                var FlightState = document.createElement("td");
                // 赋值过程
                FlightID.innerText = data[i].FlightID;
                FlightCompany.innerText = data[i].FlightCompany;
                BeginPos.innerText = data[i].BeginPos;
                DestPos.innerText = data[i].DestPos;
                Stopover.innerText = data[i].Stopover;
                PlaneGo.innerText = data[i].PlaneGo;
                PlaneArrive.innerText = data[i].PlaneArrive;
                ActuallyArrive.innerText = data[i].ActuallyArrive;
                FlightBuilding.innerText = data[i].FlightBuilding;
                GoDoor.innerText = data[i].GoDoor;
                ReadyPos.innerText = data[i].ReadyPos;
                FlightState.innerText = data[i].FlightState;
                newtr.append(FlightID);
                newtr.append(FlightCompany);
                newtr.append(BeginPos);
                newtr.append(DestPos);
                newtr.append(Stopover);
                newtr.append(PlaneGo);
                newtr.append(PlaneArrive);
                newtr.append(ActuallyArrive);
                newtr.append(FlightBuilding);
                newtr.append(GoDoor);
                newtr.append(ReadyPos);
                newtr.append(FlightState);
                $("#ShowInfo").append(newtr);
            }
        }else if(field == "FlushBaggage"){
            for(var i = 0;i < length(data);++i) {
                var newtr = document.createElement("tr");
                newtr.className = "active";
                var FlightID = document.createElement("td");
                var FlightCompany = document.createElement("td");
                var ActuallyArrive = document.createElement("td");
                var BaggagePos = document.createElement("td");
                // 赋值过程
                FlightID.innerText = data[i].FlightID;
                FlightCompany.innerText = data[i].FlightCompany;
                ActuallyArrive.innerText = data[i].ActuallyArrive;
                BaggagePos.innerText = data[i].BaggagePos;
                newtr.append(FlightID);
                newtr.append(FlightCompany);
                newtr.append(ActuallyArrive);
                newtr.append(BaggagePos);
                $("ShowInfo").append(newtr);
            }
        }else{
            alert("Empty");
        }
    });
}
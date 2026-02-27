import { Icon } from "@iconify/react";

export default function PlantForm(handleCancel){
    return (
        <div className="bg-white w-1/2 h-10/12 rounded-xl">
            <form className="flex justify-between m-2">
                <label className="text-2xl font-semibold ms-6 mt-2"> 
                    Crear Planta 
                </label>
                <button className="btn btn-circle">
                    <Icon icon={"mdi:close"} width={24} height={24} />
                </button>
            </form>
            <form action="">
                
            </form>
        </div>
    );
}
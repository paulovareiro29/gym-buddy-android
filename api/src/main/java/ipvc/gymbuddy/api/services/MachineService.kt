package ipvc.gymbuddy.api.services

import ipvc.gymbuddy.api.core.HttpClient
import ipvc.gymbuddy.api.core.ResponseParser
import ipvc.gymbuddy.api.core.RequestResult
import ipvc.gymbuddy.api.interfaces.IMachineService
import ipvc.gymbuddy.api.models.requests.machine.CreateMachineRequest
import ipvc.gymbuddy.api.models.requests.machine.UpdateMachineRequest
import ipvc.gymbuddy.api.models.responses.machine.CreateMachineResponse
import ipvc.gymbuddy.api.models.responses.machine.DeleteMachineResponse
import ipvc.gymbuddy.api.models.responses.machine.GetAllMachinesResponse
import ipvc.gymbuddy.api.models.responses.machine.UpdateMachineResponse

class MachineService : HttpClient<IMachineService>(IMachineService::class.java) {

    suspend fun getMachines(): RequestResult<GetAllMachinesResponse> {
        return when (val response = request(api.getMachines())) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<GetAllMachinesResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun createMachine(body: CreateMachineRequest): RequestResult<CreateMachineResponse> {
        return when (val response = request(api.createMachine(body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<CreateMachineResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun updateMachine(id: String, body: UpdateMachineRequest): RequestResult<UpdateMachineResponse> {
        return when (val response = request(api.updateMachine(id, body))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<UpdateMachineResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }

    suspend fun deleteMachine(id: String): RequestResult<DeleteMachineResponse> {
        return when(val response = request(api.deleteMachine(id))) {
            is RequestResult.Success -> RequestResult.Success(
                code = response.code,
                message = response.message,
                data = ResponseParser.payload<DeleteMachineResponse>(response)
            )
            is RequestResult.Error -> response
        }
    }
}
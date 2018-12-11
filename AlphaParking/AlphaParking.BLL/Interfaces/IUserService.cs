using AlphaParking.BLL.DTO;
using AlphaParking.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL
{
    public interface IUserService: ICRUDService<UserDTO>
    {
        Task<UserDTO> GetUser(int userId);
        Task<IEnumerable<CarDTO>> GetUserCars(int userId);
        Task<IEnumerable<ParkingSpaceDTO>> GetUserParkingPlaces(int userId);
    }
}

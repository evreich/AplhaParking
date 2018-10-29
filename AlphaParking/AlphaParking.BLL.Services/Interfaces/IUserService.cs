using AlphaParking.BLL.Services.DTO;
using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public interface IUserService: ICRUDService<UserDTO>
    {
        Task<UserDTO> GetUser(Guid userId);
        Task<IEnumerable<CarDTO>> GetUserCars(Guid userId);
        Task<IEnumerable<ParkingSpaceDTO>> GetUserParkingPlaces(Guid userId);
        void AddRoleToUser(UserDTO user, RoleDTO role);
        Task<bool> IsRegistered(string login, string password);
    }
}

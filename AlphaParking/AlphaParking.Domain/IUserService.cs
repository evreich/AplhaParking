using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Interfaces
{
    public interface IUserService: ICRUDService<User>
    {
        Task<User> Login(string login, string password);
        Task<User> GetUser(Guid userId);
        Task<IEnumerable<ParkingSpace>> GetUserParkingPlaces(Guid userId);
    }
}

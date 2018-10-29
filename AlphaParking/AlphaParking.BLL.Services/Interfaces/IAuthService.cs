using AlphaParking.BLL.Services.DTO;
using AlphaParking.DB.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL.Services
{
    public interface IAuthService: IDisposable
    {
        Task<AuthInfo> Login(string login, string password);
        Task<AuthInfo> Registration(UserDTO user);
        string GenerateJwtToken(UserDTO user);
    }
}

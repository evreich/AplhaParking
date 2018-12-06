using AlphaParking.BLL.DTO;
using AlphaParking.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace AlphaParking.BLL
{
    public interface IAuthService: IDisposable
    {
        Task<AuthInfo> Login(string login, string password);
        Task<AuthInfo> Registration(UserDTO user);
        string GenerateJwtToken(UserDTO user);
    }
}

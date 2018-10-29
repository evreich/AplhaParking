using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Services.DTO
{
    public class UserDTO
    {
        public Guid Id { get; set; }
        public string Login { get; set; }
        public string Password { get; set; }
        public string FIO { get; set; }
        public string Address { get; set; }
        public string Phone { get; set; }
        public string Email { get; set; }

        public List<CarDTO> Cars { get; set; } = new List<CarDTO>();
        public List<RoleDTO> Roles { get; set; } = new List<RoleDTO>();
    }
}

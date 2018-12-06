using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO
{
    public class RoleDTO
    {
        public Guid Id { get; set; }
        public string Name { get; set; }

        public List<UserDTO> Users { get; set; } = new List<UserDTO>();
    }
}

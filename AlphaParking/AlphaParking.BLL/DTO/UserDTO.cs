using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.DTO
{
    public class UserDTO
    {
        public int Id { get; set; }
        public string FIO { get; set; }
        public string Phone { get; set; }

        public List<CarDTO> Cars { get; set; }
    }
}

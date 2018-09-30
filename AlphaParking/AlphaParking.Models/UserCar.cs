using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class UserCar
    {
        [Key]
        public Guid UserId { get; set; }
        public virtual User User { get; set; }

        [Key]
        public string CarNumber { get; set; }
        public virtual Car Car { get; set; }
    }
}

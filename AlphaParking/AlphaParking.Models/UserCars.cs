using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class UserCars
    {
        [Key]
        public Guid UserId { get; set; }
        public User User { get; set; }

        [Key]
        public string CarNumber { get; set; }
        public Car Car { get; set; }
    }
}

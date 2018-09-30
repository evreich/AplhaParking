using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace AlphaParking.DB.Models
{
    public class UserRole
    {
        [Key]
        public Guid UserId { get; set; }
        public virtual User User { get; set; }

        [Key]
        public Guid RoleId { get; set; }
        public virtual Role Role { get; set; }
    }
}

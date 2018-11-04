using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Services.Exceptions
{
    public class BadRequestException: Exception
    {
        public BadRequestException(string msg): base(msg)
        { }
    }
}

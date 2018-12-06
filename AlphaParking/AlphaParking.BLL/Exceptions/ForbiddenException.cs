using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Exceptions
{
    public class ForbiddenException: Exception
    {
        public ForbiddenException(string msg):base(msg)
        { }
    }
}

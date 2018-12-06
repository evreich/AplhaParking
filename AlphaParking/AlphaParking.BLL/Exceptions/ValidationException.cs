using System;
using System.Collections.Generic;
using System.Text;

namespace AlphaParking.BLL.Exceptions
{
    public class ValidationException: Exception
    {
        public ValidationException(string msg): base(msg)
        { }
    }
}

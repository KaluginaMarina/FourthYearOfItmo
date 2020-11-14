#include <stdio.h>

//Taylor polynome numerators
const double numerator[] =
    {1, 1, 2, 17, 62, 1382, 21844, 929569, 6404582, 443861162,
    18888466084, 113927491862, 58870668456604
    };

//Taylor polynome denominators
const double denominator[] =
    {1, 3, 15, 315, 2835, 155925, 6081075,
    638512875, 10854718875, 1856156927625,
    194896477400625, 49308808782358125, 3698160658676859375
    };

const double accuracy = 10e-6;

double taylor_tan(double x)
{
    double tay_tan = 0.;
    double power_x = x;
    unsigned int i = 0;

    while (i < 13 
            || (numerator[i]*power_x)/(denominator[i]) - (numerator[i+1]*power_x)/(denominator[i+1]) > accuracy 
            || (numerator[i]*power_x)/(denominator[i]) - (numerator[i+1]*power_x)/(denominator[i+1]) < -accuracy 
           ){
        tay_tan += (numerator[i]*power_x)/(denominator[i]);
        power_x *= x * x; 
        i++;
    }
    return tay_tan;
}

int main()
{
    printf("%f", taylor_tan(0.7));

    return 0;
}

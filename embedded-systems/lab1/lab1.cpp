#include "hal.h"

int umain(){
    int i = 0, delay = 500;
    int leds_num[] = {GPIO_PIN_3,GPIO_PIN_4,GPIO_PIN_5,GPIO_PIN_6,GPIO_PIN_8,GPIO_PIN_9,GPIO_PIN_11,GPIO_PIN_12};

    while(1){

        for (i = 0; i < 4; i++){
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4], GPIO_PIN_SET);
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4 + 4], GPIO_PIN_SET);
            HAL_Delay(delay);
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4], GPIO_PIN_RESET);
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4 + 4], GPIO_PIN_RESET);
            HAL_Delay(delay);
        }


        for (i = 2; i >= 0; i--){
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4], GPIO_PIN_SET);
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4 + 4], GPIO_PIN_SET);
            HAL_Delay(delay);
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4], GPIO_PIN_RESET);
            HAL_GPIO_WritePin(GPIOD, leds_num[i%4 + 4], GPIO_PIN_RESET);
            HAL_Delay(delay);
        }
    }

    return 0;
}

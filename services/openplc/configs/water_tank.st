PROGRAM program0
  VAR
    Pool_LLS AT %IX100 : BOOL;
    Tank_LLS AT %IX101 : BOOL;
    Tank_HLS AT %IX102 : BOOL;
    Motor_ON AT %QX100 : BOOL;
  END_VAR
  VAR
    SR0 : SR;
  END_VAR

  SR0(S1 := NOT(Tank_LLS), R := NOT(Pool_LLS) OR Tank_HLS);
  Motor_ON := SR0.Q1;
END_PROGRAM


CONFIGURATION Config0

  RESOURCE Res0 ON PLC
    TASK task0(INTERVAL := T#20ms,PRIORITY := 0);
    PROGRAM instance0 WITH task0 : program0;
  END_RESOURCE
END_CONFIGURATION

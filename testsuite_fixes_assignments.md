# Failed tests that need to be fixed #

### C level ###
  * C3 - big number **Luke DONE**

### B level ###
  * B2 - downto has wrong terminating condition - should go one lower **Mahesh -- DONE**
  * B3 - writeln **Luke DONE**
  * B8 - nasty if (deeply nested else blocks) **Mahesh -- DONE**

### A level ###
  * A02 **Larry -- now passes DONE**
  * A05 **Larry -- now passes DONE**
  * A08 **Larry -- now passes DONE**
  * A09 **Larry -- now passes DONE**
  * A10 **Larry -- now passes DONE**
  * A12 -- had factorial close to right, but had some un-allocated memory **Larry DONE -could use double check**

### Misc ###
  * update buildfile, compile the compiler & compile all the test-suite to IR, also update clean **Larry DONE**
  * fix numbered grammer, allow right recursion on write parameter tail **Luke DONE**
  * update write statement to handle multiwrite  **Luke DONE**
  * check input santization of data types on read and multi-read
  * fix read into a parameter that was passed by reference **Larry DONE**
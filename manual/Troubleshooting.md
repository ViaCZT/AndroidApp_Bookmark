# Troubleshooting

If you receive an error about the Intel HAXM Manager (or similar) not being installed properly, these steps should fix it.

First, you must determine your CPU manufacturer. If you aren't certain, follow the instructions below.

| Operating system | Instructions |
|---------|---------|
| Windows 7/10 | Press **Windows + X** > **System**, or search for **System Information** in the search bar. Determine whether the CPU is Intel or AMD by looking at the processor name. |
| Windows 8 | Press **Windows + R**, type `msinfo32` and click OK. Determine whether the CPU is Intel or AMD by looking at the processor name.
| Linux | Open a new terminal and run `lscpu`. Check whether the vendor ID is `GenuineIntel` (Intel) or `AuthenticAMD` (AMD). |
| MacOS | If your device was manufactured before 2020, your CPU manufacturer is almost certainly Intel. If it was made after 2020, and you possess an M-series processor, please speak to your tutor. As of writing, no solution has been discovered on the newer M-series processors.

In Android Studio, go to **Tools** > **SDK Manager** > **SDK Tools**. The precise steps to fix this issue depend on your CPU manufacturer.

| CPU Manufacturer | Instructions |
|---------|---------|
| Intel |  Check 'Intel x86 Emulator Accelerator (HAXM Installer)' and click **OK**. Follow the installer. 
| AMD | Check 'Android Emulator Hypervisor Driver for AMD Processors (installer)' and click OK. Follow the installer.

Once you have finished the steps for your CPU manufacturer, make sure that 'Android Emulator' (still in **Tools** > **SDK Manager** > **SDK Tools**) is fully checked.

If you are on Windows and still having problems:
- If your CPU is manufactured by Intel, there may be an installer in `C:\users\%Your_Username%\AppData\Local\Android\sdk\extras\intel\Hardware_Accelerated_Execution_Manager\` you can run.
- You may need to enable Virtualisation in your BIOS. You can follow [this YouTube tutorial](https://www.youtube.com/watch?v=Y1WhS2yuF8I), but the steps after you restart your computer will depend on your motherboard BIOS. Feel free to seek help from your tutor.

If following these steps does not resolve the issue, let your tutor know. They can attempt to assist you with troubleshooting. Please be patient, as there are a large number of potential causes for these issues. If the tutor has not encountered this specific error before, you will need to work together to solve it.

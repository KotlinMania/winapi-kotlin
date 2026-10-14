#ifndef WINAPI_WRAPPER_H
#define WINAPI_WRAPPER_H

#include <stdint.h>
#include <stddef.h>

#ifdef __cplusplus
extern "C" {
#endif

/* Handle and error handling functions (handleapi.h, errhandlingapi.h, winbase.h) */
int32_t winapi_close_handle(int64_t hObject);
uint32_t winapi_get_last_error(void);
void winapi_set_last_error(uint32_t dwErrCode);
void winapi_set_last_error_ex(uint32_t dwErrCode, uint32_t dwType);
int64_t winapi_local_free(int64_t hMem);
int64_t winapi_global_free(int64_t hMem);
int32_t winapi_free_library(int64_t hLibModule);
int32_t winapi_set_handle_information(int64_t hObject, uint32_t dwMask, uint32_t dwFlags);
int32_t winapi_get_handle_information(int64_t hObject, uint32_t *lpdwFlags);
int32_t winapi_duplicate_handle(
    int64_t hSourceProcessHandle,
    int64_t hSourceHandle,
    int64_t hTargetProcessHandle,
    int64_t *lpTargetHandle,
    uint32_t dwDesiredAccess,
    int32_t bInheritHandle,
    uint32_t dwOptions
);
int32_t winapi_compare_object_handles(int64_t hFirstObjectHandle, int64_t hSecondObjectHandle);
uint32_t winapi_rtl_nt_status_to_dos_error(int32_t status);

/* OLE Automation BSTR functions (oleauto.h) */
int64_t winapi_sys_alloc_string(int64_t psz);
int64_t winapi_sys_alloc_string_byte_len(int64_t psz, uint32_t len);
int64_t winapi_sys_alloc_string_len(int64_t strIn, uint32_t ui);
void winapi_sys_free_string(int64_t bstrString);
int32_t winapi_sys_re_alloc_string(int64_t *pbstr, int64_t psz);
int32_t winapi_sys_re_alloc_string_len(int64_t *pbstr, int64_t psz, uint32_t len);
int32_t winapi_sys_add_ref_string(int64_t bstrString);
void winapi_sys_release_string(int64_t bstrString);
uint32_t winapi_sys_string_byte_len(int64_t bstr);
uint32_t winapi_sys_string_len(int64_t pbstr);

/* File system functions (fileapi.h) */
uint32_t winapi_get_file_attributes_w(int64_t lpFileName);
int32_t winapi_set_file_attributes_w(int64_t lpFileName, uint32_t dwFileAttributes);
uint32_t winapi_get_file_attributes_a(int64_t lpFileName);
int32_t winapi_set_file_attributes_a(int64_t lpFileName, uint32_t dwFileAttributes);
int64_t winapi_create_file_w(
    int64_t lpFileName,
    uint32_t dwDesiredAccess,
    uint32_t dwShareMode,
    int64_t lpSecurityAttributes,
    uint32_t dwCreationDisposition,
    uint32_t dwFlagsAndAttributes,
    int64_t hTemplateFile
);
int64_t winapi_create_file_a(
    int64_t lpFileName,
    uint32_t dwDesiredAccess,
    uint32_t dwShareMode,
    int64_t lpSecurityAttributes,
    uint32_t dwCreationDisposition,
    uint32_t dwFlagsAndAttributes,
    int64_t hTemplateFile
);
int32_t winapi_delete_file_w(int64_t lpFileName);
int32_t winapi_delete_file_a(int64_t lpFileName);
int32_t winapi_flush_file_buffers(int64_t hFile);
int32_t winapi_are_file_apis_ansi(void);
uint32_t winapi_get_file_size(int64_t hFile, uint32_t *lpFileSizeHigh);
uint32_t winapi_set_file_pointer(
    int64_t hFile,
    int32_t lDistanceToMove,
    int32_t *lpDistanceToMoveHigh,
    uint32_t dwMoveMethod
);

/* Process and thread functions (processthreadsapi.h, psapi.h, timezoneapi.h, winuser.h, namedpipeapi.h) */
int64_t winapi_get_current_process(void);
uint32_t winapi_get_current_process_id(void);
int64_t winapi_get_current_thread(void);
uint32_t winapi_get_current_thread_id(void);
int32_t winapi_terminate_process(int64_t hProcess, uint32_t uExitCode);
void winapi_exit_process(uint32_t uExitCode);
int64_t winapi_open_process(uint32_t dwDesiredAccess, int32_t bInheritHandle, uint32_t dwProcessId);
int32_t winapi_k32_empty_working_set(int64_t hProcess);
uint32_t winapi_get_time_zone_information(int64_t lpTimeZoneInformation);
int32_t winapi_lock_work_station(void);
int32_t winapi_disconnect_named_pipe(int64_t hNamedPipe);

#ifdef __cplusplus
}
#endif

#endif /* WINAPI_WRAPPER_H */

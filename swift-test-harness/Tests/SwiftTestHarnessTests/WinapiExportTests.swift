import Testing
import Winapi

@Suite("Winapi Export Smoke Tests")
struct WinapiExportTests {
    @Test("Swift module loads cleanly")
    func testSwiftModuleLoads() throws {
        #expect(true)
    }
}

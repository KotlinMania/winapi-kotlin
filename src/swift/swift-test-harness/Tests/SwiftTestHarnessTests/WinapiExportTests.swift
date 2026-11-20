#if canImport(Testing)
import Testing
import Winapi

private typealias WinapiShared = ExportedKotlinPackages.io.github.kotlinmania.winapi.shared
private typealias WinapiUm = ExportedKotlinPackages.io.github.kotlinmania.winapi.um

@Suite("Winapi Swift Export Tests")
struct WinapiExportTests {
    @Test("Minwindef and Windef types and functions work from Swift")
    func testMinwindefAndWindef() throws {
        let word = WinapiShared.minwindef.MAKEWORD(a: 0x34, b: 0x12)
        #expect(word == 0x1234)
        #expect(WinapiShared.minwindef.LOBYTE(l: word) == 0x34)
        #expect(WinapiShared.minwindef.HIBYTE(l: word) == 0x12)
        #expect(WinapiShared.minwindef.MAX_PATH == 260)

        let rect = WinapiShared.windef.RECT(left: 10, top: 20, right: 100, bottom: 200)
        #expect(rect.left == 10)
        #expect(rect.top == 20)
        #expect(rect.right == 100)
        #expect(rect.bottom == 200)
    }

    @Test("GUID equality works from Swift")
    func testGuid() throws {
        let nullGuid = WinapiShared.guiddef.IID_NULL
        #expect(WinapiShared.guiddef.IsEqualGUID(g1: nullGuid, g2: nullGuid))
    }

    @Test("Winnt and Minwinbase symbols work from Swift")
    func testWinntAndMinwinbase() throws {
        let langId = WinapiUm.winnt.MAKELANGID(p: 0x09, s: 0x01)
        #expect(langId == 0x0409)
        #expect(WinapiUm.winnt.PRIMARYLANGID(lgid: langId) == 0x09)
        #expect(WinapiUm.winnt.SUBLANGID(lgid: langId) == 0x01)

        let sa = WinapiUm.minwinbase.SECURITY_ATTRIBUTES(
            nLength: 24,
            lpSecurityDescriptor: 0,
            bInheritHandle: WinapiShared.minwindef.TRUE
        )
        #expect(sa.nLength == 24)
        #expect(sa.bInheritHandle == WinapiShared.minwindef.TRUE)
    }
}
#elseif canImport(XCTest)
import XCTest
import Winapi

private typealias WinapiShared = ExportedKotlinPackages.io.github.kotlinmania.winapi.shared
private typealias WinapiUm = ExportedKotlinPackages.io.github.kotlinmania.winapi.um

final class WinapiExportTests: XCTestCase {
    func testMinwindefAndWindef() throws {
        let word = WinapiShared.minwindef.MAKEWORD(a: 0x34, b: 0x12)
        XCTAssertEqual(word, 0x1234)
        XCTAssertEqual(WinapiShared.minwindef.LOBYTE(l: word), 0x34)
        XCTAssertEqual(WinapiShared.minwindef.HIBYTE(l: word), 0x12)
        XCTAssertEqual(WinapiShared.minwindef.MAX_PATH, 260)

        let rect = WinapiShared.windef.RECT(left: 10, top: 20, right: 100, bottom: 200)
        XCTAssertEqual(rect.left, 10)
        XCTAssertEqual(rect.top, 20)
        XCTAssertEqual(rect.right, 100)
        XCTAssertEqual(rect.bottom, 200)
    }

    func testGuid() throws {
        let nullGuid = WinapiShared.guiddef.IID_NULL
        XCTAssertTrue(WinapiShared.guiddef.IsEqualGUID(g1: nullGuid, g2: nullGuid))
    }

    func testWinntAndMinwinbase() throws {
        let langId = WinapiUm.winnt.MAKELANGID(p: 0x09, s: 0x01)
        XCTAssertEqual(langId, 0x0409)
        XCTAssertEqual(WinapiUm.winnt.PRIMARYLANGID(lgid: langId), 0x09)
        XCTAssertEqual(WinapiUm.winnt.SUBLANGID(lgid: langId), 0x01)

        let sa = WinapiUm.minwinbase.SECURITY_ATTRIBUTES(
            nLength: 24,
            lpSecurityDescriptor: 0,
            bInheritHandle: WinapiShared.minwindef.TRUE
        )
        XCTAssertEqual(sa.nLength, 24)
        XCTAssertEqual(sa.bInheritHandle, WinapiShared.minwindef.TRUE)
    }
}
#endif
